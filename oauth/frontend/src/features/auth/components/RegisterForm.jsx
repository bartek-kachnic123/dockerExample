import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import useRegister from '../hooks/useRegister.js';
import registerSchema from '../schemas/registerSchema.js';
import FieldError from './FieldError.jsx';
import FormInput from './FormInput.jsx';
import SubmitButton from './SubmitButton.jsx';

export default function RegisterForm() {
  const { registerUser, loading, serverError } = useRegister();

  const {
    register: registerFields,
    handleSubmit,
    formState: { errors },
      reset
  } = useForm({
    resolver: yupResolver(registerSchema),
  });

  const onSubmit = async (formData) => {
    await registerUser(formData);
    reset();
  };

  const fields = {
    email: registerFields("email"),
    password: registerFields("password"),
    confirmPassword: registerFields("confirmPassword"),
  };

  return (
      <div className="flex items-center justify-center">
        <form
            onSubmit={handleSubmit(onSubmit)}
            className="flex-auto bg-white/40 backdrop-blur-md shadow-2xl rounded-3xl p-8 w-full max-w-md border border-white/30"
        >
          <h2 className="text-3xl font-bold text-center text-gray-800 mb-8 drop-shadow-md">
            Register
          </h2>
          <FieldError error={serverError} />
          <FormInput
              label="Email"
              type="email"
              controlProps={fields.email}
              placeholder="Enter your email"
              error={errors.email}
          />
          <FormInput
              label="Password"
              type="password"
              controlProps={fields.password}
              placeholder="Enter your password"
              error={errors.password}
          />
          <FormInput
              label="Confirm Password"
              type="password"
              controlProps={fields.confirmPassword}
              placeholder="Confirm your password"
              error={errors.confirmPassword}
          />
          <SubmitButton loading={loading}>Register</SubmitButton>
        </form>
      </div>
  );
}
