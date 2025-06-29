import React from "react";
import { useAuthDispatch, useAuthState } from "../context";
import { useNavigate } from "react-router";
import http from "../http";

function Navbar(props) {
  const dispatch = useAuthDispatch();
  const auth = useAuthState();
  const navigate = useNavigate();


  const{username}=props

  const logout = async () => {
    dispatch({ type: "logout-success" });
    navigate("/login");
  };

  const deleteAccount = async () => {
    await http.delete("/api/v1/users/delete/" + auth);
    logout();
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container-fluid">
        <a className="navbar-brand" href="#">
          Microservice
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
            <li className="nav-item dropdown">
              <a
                className="nav-link dropdown-toggle"
                href="#"
                id="navbarDropdown"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                Profile
              </a>
              <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                <li>
                  <a className="dropdown-item" href="#">
                    {username}
                  </a>
                </li>
                <li>
                  <hr className="dropdown-divider" />
                </li>
                <li></li>
                <li className="d-grid gap-2">
                  <button
                    className="btn btn-primary"
                    onClick={() => logout()}
                  >
                    Logout
                  </button>
                  <button className="btn btn-danger" onClick={()=>deleteAccount()} >
                    Delete Account
                  </button>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
