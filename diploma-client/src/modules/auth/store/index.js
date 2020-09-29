import authService from '@/modules/auth/services/AuthService.js';
import { useLocalStoragePlugin } from '@/utils/storeUtils.js';

const mutation = {
    SET_ACCESS_TOKEN: 'SET_ACCESS_TOKEN',
    INVALIDATE_SESSION: 'INVALIDATE_SESSION',
};

export default {
    namespaced: true,
    state: {
        accessToken: null,
    },
    getters: {
        isLoggedIn: (state) => state.accessToken !== void 0 && state.accessToken !== null,
    },
    actions: {
        signIn: async ({ commit }, { username, password }) => {
            try {
                const { accessToken, tokenType } = await authService.signIn({ username, password });
                commit(mutation.SET_ACCESS_TOKEN, { accessToken, tokenType });
            } catch (e) {
                commit(mutation.INVALIDATE_SESSION);
            }
        },
        signOut: ({ commit }) => {
            commit(mutation.INVALIDATE_SESSION);
        },
    },
    mutations: {
        [mutation.SET_ACCESS_TOKEN]: (state, { accessToken, tokenType }) => {
            state.accessToken = accessToken;
            state.tokenType = tokenType;
        },
        [mutation.INVALIDATE_SESSION]: (state) => {
            state.accessToken = null;
            state.tokenType = null;
        },
    },
    plugins: [useLocalStoragePlugin('auth')],
};