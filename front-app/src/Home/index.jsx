import React, { useEffect, useState } from "react";
import http from "../http";
import { useAuthState } from "../context";

function Home() {
  const [shares, setShares] = useState([]);
  const [text, setText] = useState("");
  const loggedIdUser = useAuthState();

  const createShares = async (e) => {
    e.preventDefault();

    try {
      const create = await http.post("/api/v1/shares/create", { text });
      getShares();
    } catch (error) {
      console.log(error);
    }
  };

  const getShares = async () => {
    try {
      const sharesFromBackend = await http.get("/api/v1/shares");
      console.log(sharesFromBackend);

      setShares(sharesFromBackend.data);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getShares();
  }, []);

  return (
    <div className="container">
      <div className="my-3">
        <input
          type="text"
          className="form-control mb-3"
          name="share"
          id="share"
          onChange={(e) => setText(e.target.value)}
        />
        <button className="btn btn-primary" onClick={(e) => createShares(e)}>
          Share
        </button>
      </div>

      <hr />

      {shares.map((share) => {
        return (
          <div className="card text-white bg-dark mb-3" key={Math.random()}>
            <div className="card-header">{share.user.fullName}</div>
            <div className="card-body">
              <p className="card-text">{share.text}</p>
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default Home;
