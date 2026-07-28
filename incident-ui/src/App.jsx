import {BrowserRouter, Routes, Route} from 'react-router-dom';
import ProtectedRoute from "./components/ProtectedRoute";

import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import IncidentDetails from './pages/IncidentDetails';
import Incidents from './pages/Incidents';
import Knowledge from './pages/Knowledge';
import Ownership from './pages/Ownership';
import AIAnalyzer from "./pages/AILogAnalyzer";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/incidents" element={<Incidents />} />
        <Route path="/incidents/:id" element={<IncidentDetails />} />
        <Route path="/knowledge" element={<Knowledge />} />
        <Route path="/ownership" element={<Ownership />} />
        <Route path="/api/ai/analyse" element={<AIAnalyzer />}
/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
