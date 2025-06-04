export default function FieldError({ error, className = "text-red-500 text-sm mt-2" }) {
  if (!error?.message) return null;

  return (
      <p className={className}>{error.message}</p>
  );
}
