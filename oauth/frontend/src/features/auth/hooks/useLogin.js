import { useState } from 'react';
import api from '@api/axios.js';

export default function useLogin() {
  const [loading, setLoading] = useState(false);
  const [serverError, setServerError] = useState(null);

  const loginUser = async (formData) => {
    setLoading(true);
    setServerError(null);

    try {
      const res = await api.post('/auth/login', formData);
      const authHeader = res.headers['authorization']
      if (authHeader?.startsWith('Bearer ')) {
        const token = authHeader.substring(7);
        localStorage.setItem('jwt_token', token);
      }

      return res.data;
    } catch (err) {
      const errorMessage = err.response?.data?.message || err.message || 'An unknown error occurred. Please try again.';
      setServerError({message: errorMessage });
    } finally {
      setLoading(false);
    }
  };

  return { loginUser, loading, serverError };
}