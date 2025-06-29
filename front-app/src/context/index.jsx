import { createContext, useEffect, useContext, useReducer } from "react";
import { setCurrentToken } from "../http";

export const AuthContext = createContext();
export const AuthDispatchContext = createContext();


const authReducer = (authState, action) => {
  switch (action.type) {
    case "login-success":
      storeToken(action.data.token);
      setCurrentToken(action.data.token)
      storeRefreshToken(action.data.refreshToken);
      return action.data.authUserId;
    case "logout-success":
      logout()
      return { id: 0 };
    default:
      throw new Error(`unknown action: ${action.type}`);
  }
};


export function useAuthState() {
  return useContext(AuthContext);
}

export function useAuthDispatch() {
  return useContext(AuthDispatchContext);
}

export function storeAuthState(auth) {
  localStorage.setItem("auth", JSON.stringify(auth));
}

export function storeToken(token) {
  localStorage.setItem("token", JSON.stringify(token));
}

export function storeRefreshToken(refreshToken) {
  localStorage.setItem("refresh-token", JSON.stringify(refreshToken));
}

function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("refresh-token");
}



export function loadAuthState() {
  const defaultState = { id: 0 };
  const authStateInStorage = localStorage.getItem("auth");
  if (!authStateInStorage) return defaultState;
  try {
    return JSON.parse(authStateInStorage);
  } catch {
    return defaultState;
  }
}




export function AuthenticationContext({ children }) {
  const [authState, dispatch] = useReducer(authReducer, loadAuthState());

  useEffect(() => {
    storeAuthState(authState);
  }, [authState]);

  return (
    <AuthContext.Provider value={authState}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthContext.Provider>
  );
}
