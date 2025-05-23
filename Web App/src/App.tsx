import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css'
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";


function App() {

  return (
  <Router>
    <Routes>
      <Route path="/" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
    </Routes>
  </Router>);
}

export default App
