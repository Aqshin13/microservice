import React, { useState } from "react";
import http from "../http";
import {Link} from "react-router";

function SignUp() {
  const [userName, setUsername] = useState();
  const [password, setPassword] = useState();
  const [email, setEmail] = useState();
  const [fullName, setFullName] = useState();
  const [phoneNumber, setPhone] = useState();
  const [apiProgress, setApiProgress] = useState();
  const [validationErrors, setValidationError] = useState({});
  const [generalError, setGeneralError] = useState();
  const [successMessage, setSuccessMessage] = useState();
  const signUp = async (e) => {
    e.preventDefault();
    setApiProgress(true);
    setValidationError({});
    setGeneralError();
    try {
      const result = await http.post("/api/v1/auth/register", {
        userName,
        password,
        email,
        fullName,
        phoneNumber,
      });
       console.log(result);
       
      setSuccessMessage(result.data.message);
    } catch (error) {
      if (error.response.data?.validationErrors) {
        setValidationError(error.response.data.validationErrors);
      } else {
        setGeneralError("Unexpected Error");
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
                <h2 className="text-primary">Sign Up</h2>
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

                <div className="mb-3">
                  <label htmlFor="email" className="form-label">
                    E-mail
                  </label>
                  <input
                    type="email"
                    className={
                      validationErrors?.email
                        ? "form-control is-invalid"
                        : "form-control"
                    }
                    name="email"
                    id="email"
                    onChange={(e) => setEmail(e.target.value)}
                  />
                  {validationErrors.email && (
                    <div className="invalid-feedback">{validationErrors.email}</div>
                  )}
                </div>

                <div className="mb-3">
                  <label htmlFor="fullName" className="form-label">
                    Full Name
                  </label>
                  <input
                    type="text"
                    className={
                      validationErrors?.fullName
                        ? "form-control is-invalid"
                        : "form-control"
                    }
                    name="fullName"
                    id="fullName"
                    onChange={(e) => setFullName(e.target.value)}
                  />
                  {validationErrors.fullName && (
                    <div className="invalid-feedback">
                      {validationErrors.fullName}
                    </div>
                  )}
                </div>

                <div className="mb-3">
                  <label htmlFor="phone" className="form-label">
                    Phone
                  </label>
                  <input
                    type="text"
                    className={
                      validationErrors?.phoneNumber
                        ? "form-control is-invalid"
                        : "form-control"
                    }
                    name="phone"
                    id="phone"
                    onChange={(e) => setPhone(e.target.value)}
                  />
                  {validationErrors.phoneNumber && (
                    <div clasName="invalid-feedback">
                      {validationErrors.phoneNumber}
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
                    SignUp
                    {apiProgress && (
                      <div
                        className="spinner-border spinner-border-sm text-light"
                        role="status"
                      ></div>
                    )}
                  </button>


                 <div>
                   <Link to={"/sigup"}>Do you have an account already?</Link>
                 </div>

                  {generalError && (
                    <div
                      className="alert alert-danger mt-3 text-center"
                      role="alert"
                    >
                      <strong>{generalError}</strong>
                    </div>
                  )}

                   {successMessage && (
                    <div
                      className="alert alert-success mt-3 text-center"
                      role="alert"
                    >
                      <strong>{successMessage}</strong>
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

export default SignUp;
