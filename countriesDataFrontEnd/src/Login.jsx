import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';
import { Flex, Radio, Input, Button, Alert } from 'antd';
import axios from 'axios';

function Login() {
  const [userType, setUserType] = useState('normal-user');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleRadioChange = (e) => {
    setUserType(e.target.value);
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/auth/users/login', {
        username,
        password,
      });
      const { accessToken } = response.data;
      alert('Login successful');
      localStorage.setItem('auth_token', accessToken);
      const nextUrl = userType === "normal-user" ? "/tokens" : "/admin";
      navigate(nextUrl);
    } catch (error) {
      setErrorMessage('نام کاربری یا رمز عبور اشتباه است');
    }
  };

  return (
    <>
      <div className='box'>
        <Flex vertical gap="middle">
          <Radio.Group 
            defaultValue="normal-user" 
            buttonStyle="outline" 
            onChange={handleRadioChange}
          >
            <Radio.Button value="normal-user">کاربر عادی</Radio.Button>
            <Radio.Button value="admin">ادمین سیستم</Radio.Button>
          </Radio.Group>
        </Flex>
        <div className='input-box'>
          <div className='username-input'>
            <p>نام کاربری خود را وارد کنید</p>
            <Input 
              className='login-input' 
              placeholder="نام کاربری" 
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </div>
          <div className='password-input'>
            <p>رمز عبور خود را وارد کنید</p>
            <Input.Password 
              className='login-input' 
              placeholder="رمز عبور" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </div>
        </div>
        {errorMessage && <Alert message={errorMessage} type="error" showIcon />}
        <Button type="primary" onClick={handleLogin}>وارد شوید</Button>
        {userType === 'normal-user' && (
          <p>
            حسابی ندارید؟ <a onClick={() => navigate('/signup')}>ثبت نام کنید</a>
          </p>
        )}
      </div>
    </>
  );
}

export default Login;
