import { useEffect, useState } from 'react';
import axios from 'axios';

function Countries() {
  const [countries, setCountries] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchCountries = async () => {
      try {
        const token = localStorage.getItem('auth_token');
        const response = await axios.get('http://localhost:8080/api/v1/countries/', {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        setCountries(response.data.countries);
      } catch (error) {
        setError('Failed to fetch countries');
      }
    };

    fetchCountries();
  }, []);

  if (error) {
    return <div>{error}</div>;
  }

  return (
    <div>
      <h1>Countries</h1>
      <ul>
        {countries.map(country => (
          <li key={country.name}>{country.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default Countries;
    