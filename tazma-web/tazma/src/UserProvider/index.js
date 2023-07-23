import React, { createContext, useContext, useState } from "react";
const UserContext = createContext();

const UserProvider = ({ children }) => {
  const [jwt, setJwt] = useState("");
  const [role, setRole] = useState("");
  const [refreshToken, setRefreshToken] = useState("");

  const value = { jwt, setJwt, role, setRole, refreshToken, setRefreshToken};

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
 
};

function useUser() {
  const context = useContext(UserContext);
  if (context === undefined) {
    throw new Error("useUser must be used within a UserProvider");
  }

  return context;
}

export { UserProvider, useUser };

