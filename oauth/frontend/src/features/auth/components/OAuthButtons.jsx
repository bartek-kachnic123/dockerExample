import useOAuthLogin from '../hooks/useOAuthLogin';

export default function OAuthButtons() {
  const { loginWithProvider, loading } = useOAuthLogin();

  return (
      <div className="flex flex-col mt-4">
        <button className="px-4 py-2 bg-white text-gray-700 border border-gray-300 rounded shadow hover:bg-gray-100 transition"
                disabled={loading} onClick={() => loginWithProvider('google')}>
          Continue with Google
        </button>
        <button className="mt-2 px-4 py-2 bg-gray-900 text-white rounded shadow hover:bg-gray-800 transition"
                disabled={loading} onClick={() => loginWithProvider('github')}>
          Continue with GitHub
        </button>
      </div>
  );
}
