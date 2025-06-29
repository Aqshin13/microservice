import { Navigate, Outlet } from "react-router";

export const PublicLayout = () => {
  const isAuthenticated = localStorage.getItem("token");

  return isAuthenticated ? <Navigate to="/home"  /> : <Outlet />;
};

export default PublicLayout;