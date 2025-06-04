export default function SubmitButton({ loading, children }) {
  return (
      <button
          type="submit"
          className="w-full py-3 rounded-xl bg-gradient-to-r from-purple-500 to-pink-500 text-white font-semibold shadow-lg hover:scale-105 hover:shadow-2xl transition duration-300"
          disabled={loading}
      >
        {loading ? "Loading..." : children}
      </button>
  );
}
