import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import './App.css';
import Login from './Login';
import Signup from './Signup';
import Admin from './Admin';
import Tokens from './Tokens';

function App() {
  const isAdmin = () => {
    // Assuming the token contains user role information; adjust as necessary
    const token = localStorage.getItem('auth_token');
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub === 'admin';
    }
    return false;
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate replace to="/login" />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/admin" element={isAdmin() ? <Admin /> : <Navigate replace to="/login" />} />
        <Route path="/tokens" element={<Tokens />} />
      </Routes>
    </Router>
  );
}

export default App;
