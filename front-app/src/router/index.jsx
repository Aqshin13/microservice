import { createBrowserRouter } from "react-router";
import  SignUp  from "../Signup/index.jsx";
import  Login  from "../Login/index.jsx";
import Home from "../Home";
import { PublicLayout } from "../security/PublicLayout";
import {ProtectedLayout} from "../security/ProtectedLayout.jsx";
import App from "../App.jsx"
import Activation from "../Activition/index.jsx";
export default createBrowserRouter([
  {
    path: "/",
    Component: App,
    children: [
      {
        element: <PublicLayout />,
        children: [
          {
            path: "/",
            index: true,
            Component: SignUp,
          },
            {
              path: "/activation/:token",
              Component: Activation,
            },
          {
            path: "/login",
            Component: Login,
          },
          {
            path: "*",
            Component: Login,
          },
        ],
      },
      {
        element: <ProtectedLayout />,
        children: [
          { path: "home", Component: Home },
          { path: "*", Component: Home },
        ],
      },
      {
        path: "*",
        Component: Login,
      },
    ],
  },
]);
