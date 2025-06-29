import { data, Navigate, Outlet } from "react-router";
import Navbar from "../components/Navbar";
import { useAuthState } from "../context";
import { useEffect, useState } from "react";
import http from "../http";

export const ProtectedLayout = () => {
  const [user, setUser] = useState();
  const auth = useAuthState();
  const isAuthenticated = localStorage.getItem("token");

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  const getUser = async () => {
    const data = await http.get("/api/v1/users/" + auth);
    setUser(data.data);
  };

  useEffect(() => {
    getUser();
  }, []);

  return (
    <div className="container-sm">
     {user? <Navbar  username={user.fullName}/>:<p>LOADING</p>}
      <Outlet className="mb-4" />
    </div>
  );
};
