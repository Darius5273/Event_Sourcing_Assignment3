import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { loginUser } from "../api/user";
import "../index.css";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await loginUser(username, password);
      
      sessionStorage.setItem("user", JSON.stringify(res.data));
      navigate("/dashboard");
    } catch {
      setError("Invalid credentials.");
    }
  };

  return (
    <div className="container">
      <h2>Login</h2>
      <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
      <input placeholder="Password" type="password" value={password} onChange={e => setPassword(e.target.value)} />
      {error && <div className="error">{error}</div>}
      <button onClick={handleLogin}>Login</button>
      <p>Don't have an account? <button onClick={() => navigate("/")}>Register</button></p>
    </div>
  );
}
