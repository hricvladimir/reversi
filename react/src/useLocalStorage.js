import { useEffect, useState } from 'react';

const PREFIX = 'gamestudio-';

export const useLocalStorage = (key, initValue) => {
    const prefixedKey = PREFIX + key;

    const [value, setValue] = useState(() => {
        const jsonValue = localStorage.getItem(prefixedKey);

        if (jsonValue != null && typeof jsonValue !== 'undefined') {
            let val;
            try {
                val = JSON.parse(jsonValue);
            } catch (error) {
                val = null;
            }
            return val;
        }
        if (typeof initValue === 'function') {
            return initValue();
        } else {
            return initValue;
        }
    });

    useEffect(() => {
        localStorage.setItem(prefixedKey, JSON.stringify(value))
    }, [prefixedKey, value]);

    return [value, setValue];
}