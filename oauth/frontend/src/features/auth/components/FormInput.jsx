import FieldError from './FieldError.jsx';

export default function FormInput({ label, type, controlProps, placeholder, error }) {
  return (
      <div className="mb-6">
        <label className="block text-gray-700 font-semibold mb-2">
          {label}
        </label>
        <input
            type={type}
            {...controlProps}
            placeholder={placeholder}
            className="w-full px-4 py-2 rounded-lg border border-gray-300 shadow-inner focus:outline-none focus:ring-2 focus:ring-purple-400 transition duration-200"
        />
        <FieldError error={error} />
      </div>
  );
}
