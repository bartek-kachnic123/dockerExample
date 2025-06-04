import * as yup from 'yup';

const registerSchema = yup.object().shape({
    email: yup.string()
        .email('Invalid email address')
        .required('Email is required'),

    password: yup.string()
        .min(8, 'Password must be at least 8 characters long')
        .matches(/[0-9]/, 'Password must contain at least one number')
        .matches(/[(!@#$%^&*)]/, 'Password must contain at least one special character (!@#$%^&*)')
        .required('Password is required'),

    confirmPassword: yup.string()
        .oneOf([yup.ref('password')], 'Passwords must match')
        .required('Please confirm your password'),
});

export default registerSchema;
