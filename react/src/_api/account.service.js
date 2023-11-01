// put => api/account - account
// post => api/account - account

import gsAxios from "./index";

export const fetchAccounts = (username, hashedPassword) => gsAxios.put('/account', {
    username, hashedPassword
});
export const addAccount = (username, hashedPassword) => gsAxios.post('/account', {
    username, hashedPassword
});