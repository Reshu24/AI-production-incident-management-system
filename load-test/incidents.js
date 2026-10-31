import http from 'k6/http';
import { check, sleep } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'http://localhost:8080';

export const options = {
    stages: [
        { duration: '10s', target: 10 }, // ramp up to 10 virtual users
        { duration: '30s', target: 10 }, // hold
        { duration: '5s', target: 0 },   // ramp down
    ],
    thresholds: {
        http_req_failed: ['rate<0.01'],   // fewer than 1% failed requests
        http_req_duration: ['p(95)<500'], // 95% of requests under 500 ms
    },
};

// Runs once before the test: log in and share the token with all users
export function setup() {
    const res = http.post(
        `${BASE_URL}/auth/login`,
        JSON.stringify({ userName: 'admin1', password: 'admin123' }),
        { headers: { 'Content-Type': 'application/json' } }
    );

    check(res, { 'login succeeded': (r) => r.status === 200 });
    return { token: res.json('token') };
}

export default function (data) {
    const params = {
        headers: {
            Authorization: `Bearer ${data.token}`,
            'Content-Type': 'application/json',
        },
    };

    const list = http.get(`${BASE_URL}/api/incidents`, params);
    check(list, { 'list incidents is 200': (r) => r.status === 200 });

    const match = http.get(
        `${BASE_URL}/api/incidents/match?description=payment%20gateway%20timeout`,
        params
    );
    check(match, { 'match incidents is 200': (r) => r.status === 200 });

    sleep(1);
}