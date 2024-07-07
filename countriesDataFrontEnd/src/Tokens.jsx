import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';
import { Table, Button, Alert, Modal, Input, DatePicker } from 'antd';
import axios from 'axios';
import moment from 'moment';

function Tokens() {
  const [tokens, setTokens] = useState([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [visible, setVisible] = useState(false);
  const [tokenName, setTokenName] = useState('');
  const [expirationDate, setExpirationDate] = useState(null);
  const [generatedToken, setGeneratedToken] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTokens = async () => {
      try {
        const token = localStorage.getItem('auth_token');
        const response = await axios.get('http://localhost:8080/api/v1/user/api-tokens/', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        setTokens(response.data);
      } catch (error) {
        setErrorMessage('دریافت توکن‌ها با شکست مواجه شد');
      }
    };

    fetchTokens();
  }, []);

  const handleDeleteToken = async (tokenName) => {
    try {
      const token = localStorage.getItem('auth_token');
      await axios.delete('http://localhost:8080/api/v1/user/api-tokens/', {
        headers: {
          'Authorization': `Bearer ${token}`
        },
        data: {
          tokenName
        }
      });
      setTokens(tokens.filter(token => token.name !== tokenName));
      setSuccessMessage('توکن با موفقیت حذف شد');
    } catch (error) {
      setErrorMessage('حذف توکن با شکست مواجه شد.');
    }
  };

  const handleCreateToken = async () => {
    try {
      const token = localStorage.getItem('auth_token');
      const response = await axios.post('http://localhost:8080/api/v1/user/api-tokens/', {
        name: tokenName,
        expirationDate: expirationDate.toISOString(),
      }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setGeneratedToken(response.data.token);
      setTokens([...tokens, { name: tokenName, expirationDate }]);
      setVisible(false);
      setSuccessMessage('Token created successfully');
    } catch (error) {
      setErrorMessage('Failed to create token');
    }
  };

  const columns = [
    {
      title: 'نام توکن',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: ' تاریخ انقضا',
      dataIndex: 'expirationDate',
      key: 'expirationDate',
      render: (text) => moment(text).format('YYYY-MM-DD'),
    },
    {
      title: 'عملیات',
      key: 'action',
      render: (text, record) => (
        <Button onClick={() => handleDeleteToken(record.name)}>
          ابطال
        </Button>
      ),
    },
  ];

  const showModal = () => {
    setVisible(true);
  };

  const handleCancel = () => {
    setVisible(false);
    setGeneratedToken('');
    setTokenName('');
    setExpirationDate(null);
  };

  return (
    <>
      <div className='box'>
        {errorMessage && <Alert message={errorMessage} type="error" showIcon />}
        {successMessage && <Alert message={successMessage} type="success" showIcon />}
        <Table dataSource={tokens} columns={columns} rowKey="name" />
        <Button type="primary" onClick={showModal}>ساخت توکن جدید</Button>
        <Modal
          title="ساخت توکن API جدید"
          visible={visible}
          onOk={handleCreateToken}
          onCancel={handleCancel}
        >
          <Input 
            placeholder="نام توکن" 
            value={tokenName} 
            onChange={(e) => setTokenName(e.target.value)} 
          />
          <DatePicker 
            style={{ width: '100%', marginTop: '10px' }} 
            onChange={(date) => setExpirationDate(date)} 
          />
          {generatedToken && (
            <div>
              <p>Generated Token:</p>
              <Input 
                value={generatedToken} 
                readOnly 
                addonAfter={<Button onClick={() => navigator.clipboard.writeText(generatedToken)}>کپی کردن</Button>} 
              />
            </div>
          )}
        </Modal>
        <Button type="default" onClick={() => navigate('/login')}>بازگشت به صفحه لاگین</Button>
      </div>
    </>
  );
}

export default Tokens;
