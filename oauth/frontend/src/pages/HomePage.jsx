import { useEffect, useState } from "react";
import api from "@api/axios.js";
import useOAuthLogin from "../features/auth/hooks/useOAuthLogin.js";

export default function HomePage() {
  const [currentUser, setCurrentUser] = useState(null);
  const { handleOAuthRedirect } = useOAuthLogin();

  useEffect(() => {
    handleOAuthRedirect();

    api.get("/auth/user")
        .then((res) => setCurrentUser(res.data))
        .catch(() => setCurrentUser(null));
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("jwt_token");

    const cleanUrl = window.location.origin + window.location.pathname;
    window.history.replaceState(null, "", cleanUrl);

    setCurrentUser(null);
  };

  return (
      <div className="my-auto flex flex-col items-center">
        <h1>HomePage</h1>
        {currentUser && (
            <>
              <p className="mt-4">Your login is {currentUser}.</p>
              <button
                  className="mt-4 px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700"
                  onClick={handleLogout}
              >
                Logout
              </button>
            </>
        )}
      </div>
  );
}