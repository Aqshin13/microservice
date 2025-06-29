import { useState } from 'react'
import { AuthenticationContext } from "./context";
import { Outlet } from "react-router";

function App() {

  return (
    <>
      <AuthenticationContext>
        <Outlet />
      </AuthenticationContext>
    </>
  )
}

export default App
