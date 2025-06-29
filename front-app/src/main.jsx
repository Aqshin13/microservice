import { createRoot } from "react-dom/client";
import router from "./router/index.jsx";
import "./styles.scss";
import "./style.js"
import { RouterProvider } from "react-router";
createRoot(document.getElementById("root")).render(
  <RouterProvider router={router} />
);
