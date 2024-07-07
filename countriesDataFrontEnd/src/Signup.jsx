import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';
import { Flex, Input, Button, Alert } from 'antd';
import axios from 'axios';

function Signup() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  const handleSignup = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/v1/auth/users/register', {
        username,
        password,
      });
      setSuccessMessage('ثبت نام شما با موفقیت انجام شد. به صفحه ورود بازگردید.');
      setErrorMessage('');
    } catch (error) {
      setErrorMessage('ثبت نام شما با شکست مواجه شد. لطفا مجدد امتحان کنید.');
      setSuccessMessage('');
    }
  };

  return (
    <>
      <div className='box'>
        <Flex vertical gap="middle">
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
        </Flex>
        {errorMessage && <Alert message={errorMessage} type="error" showIcon />}
        {successMessage && <Alert message={successMessage} type="success" showIcon />}
        <Button type="primary" onClick={handleSignup}>ثبت نام</Button>
        <Button type="default" onClick={() => navigate('/login')}>بازگشت</Button>
      </div>
    </>
  );
}

export default Signup;
