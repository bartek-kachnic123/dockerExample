import { useState } from 'react';
import api from '@api/axios';

export default function useRegister() {
    const [loading, setLoading] = useState(false);
    const [serverError, setServerError] = useState(null);

    const registerUser = async (formData) => {
        setLoading(true);
        setServerError(null);

        try {
            const res = await api.post('/auth/register', formData);

            return res.data;
        } catch (err) {
            const errorMessage = err.response?.data?.message || err.message || 'An unknown error occurred. Please try again.';
            setServerError({message: errorMessage });
        } finally {
            setLoading(false);
        }
    };

    return { registerUser, loading, serverError };
};
