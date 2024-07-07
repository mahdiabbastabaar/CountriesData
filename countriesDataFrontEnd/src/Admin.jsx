import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';
import { Table, Button, Alert, Switch } from 'antd';
import axios from 'axios';
import moment from 'moment';

function Admin() {
  const [users, setUsers] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const token = localStorage.getItem('auth_token');
        const response = await axios.get('http://localhost:8080/api/v1/auth/admin/users', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        console.log("fetchUsers", response.data)
        setUsers(response.data);
      } catch (error) {
        setErrorMessage('دریافت لیست کاربران با خطا مواجه شد');
      }
    };

    fetchUsers();
  }, []);

  const handleStatusChange = async (username, currentStatus) => {
    try {
      const token = localStorage.getItem('auth_token');
      const newStatus = !currentStatus;
      await axios.put(`http://localhost:8080/api/v1/auth/admin/users?username=${username}&active=${newStatus}`, null, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      try {
        const token = localStorage.getItem('auth_token');
        const response = await axios.get('http://localhost:8080/api/v1/auth/admin/users', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        console.log("fetchUsers", response.data)
        setUsers(response.data);
      } catch (error) {
        setErrorMessage('دریافت لیست کاربران با خطا مواجه شد');
      }
    } catch (error) {
      setErrorMessage('تعویض وضعیت کاربر با شکست مواجه شد.');
    }
  };

  const columns = [
    {
      title: 'نام کاربری',
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: 'تاریخ ثبت نام ',
      dataIndex: 'signupDate',
      key: 'signupDate',
      render: (text) => moment(text).format('YYYY-MM-DD'),
    },
    {
      title: 'وضعیت',
      dataIndex: 'active',
      key: 'active',
      render: (text, record) => (
        <Switch 
          checked={record.enabled}
          onChange={() => handleStatusChange(record.username, record.enabled)}
          checkedChildren="فعال"
          unCheckedChildren="غیرفعال"
        />
      ),
    },
  ];

  return (
    <>
      <div className='box'>
        {errorMessage && <Alert message={errorMessage} type="error" showIcon />}
        <Table dataSource={users} columns={columns} rowKey="username" />
        <Button type="default" onClick={() => navigate('/login')}>بازگشت به صفحه ورود</Button>
      </div>
    </>
  );
}

export default Admin;
