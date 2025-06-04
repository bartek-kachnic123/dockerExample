import { useState } from 'react';

export default function useOAuthLogin() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const loginWithProvider = (provider) => {
    setLoading(true);
    setError(null);

    try {
      window.location.href = `http://localhost:8080/oauth2/authorization/${provider}`;
    } catch (err) {
      setError({ message: err.message });
      setLoading(false);
    }
  };

  const handleOAuthRedirect = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');

    if (token) {
      localStorage.setItem('jwt_token', token);
    }
  };

  return {
    loginWithProvider,
    handleOAuthRedirect,
    loading,
    error,
  };
}
