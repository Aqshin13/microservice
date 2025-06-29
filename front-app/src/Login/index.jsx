import React, { useState } from "react";
import http from "../http";
import {useAuthDispatch} from "../context/index.jsx";
import {Link, useNavigate} from "react-router";
function Login() {
  const [userName, setUsername] = useState();
  const [password, setPassword] = useState();
  const [apiProgress, setApiProgress] = useState();
  const [validationErrors, setValidationError] = useState({});
  const [generalError, setGeneralError] = useState();

  const dispatch = useAuthDispatch();
  const navigate = useNavigate();
  const signUp = async (e) => {
    e.preventDefault();
    setApiProgress(true);
    setValidationError({});
    setGeneralError();
    try {
      const result = await http.post("/api/v1/auth/login", {
        userName,
        password,
      });
      dispatch({ type: "login-success", data: result.data });
      navigate("/home");
    } catch (error) {
      console.log(error);

      if (error.response.data?.validationErrors) {
        setValidationError(error.response.data.validationErrors);
      } else {
        setGeneralError(error.response.data?.message);
      }
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <div>
      <div className="container">
        <div className="raw p-5">
          <div className="col-4 offset-4">
            <div className="card shadow-lg ">
              <div className="card-header text-center">
                <h2 className="text-primary">LOGIN</h2>
              </div>

              <form className="card-body">
                <div className="mb-3">
                  <label htmlFor="username" className="form-label">
                    Username
                  </label>
                  <input
                    type="text"
                    className={
                      validationErrors?.userName
                        ? "form-control is-invalid"
                        : "form-control"
                    }
                    name="username"
                    id="username"
                    onChange={(e) => setUsername(e.target.value)}
                  />
                  {validationErrors.userName && (
                    <div className="invalid-feedback">
                      {validationErrors.userName}
                    </div>
                  )}
                </div>

                <div className="mb-3">
                  <label htmlFor="password" className="form-label">
                    Password
                  </label>
                  <input
                    type="password"
                    className={
                      validationErrors?.password
                        ? "form-control is-invalid"
                        : "form-control"
                    }
                    name="password"
                    id="password"
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  {validationErrors.password && (
                    <div className="invalid-feedback">
                      {validationErrors.password}
                    </div>
                  )}
                </div>

                <div className="text-center">
                  <button
                    className="btn btn-primary"
                    type="submit"
                    onClick={(e) => signUp(e)}
                    disabled={apiProgress}
                  >
                    Login
                    {apiProgress && (
                      <div
                        className="spinner-border spinner-border-sm text-light"
                        role="status"
                      ></div>
                    )}
                  </button>

                  <div>
                    <Link to={"/"}>Did you have an account yet?</Link>
                  </div>

                  {generalError && (
                    <div
                      className="alert alert-danger mt-3 text-center"
                      role="alert"
                    >
                      <strong>{generalError}</strong>
                    </div>
                  )}
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Login;
