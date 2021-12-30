module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/vue3-essential',
    'eslint:recommended',
    '@vue/typescript/recommended'
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    //rules for eslint
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    //custom rules
    'vue/no-unused-components': 'off',
    '@typescript-eslint/no-explicit-any': 0,
    '@typescript-eslint/ban-types': 'off',
    'vue/no-unused-vars': 0,
    '@typescript-eslint/no-unused-vars': 0,
  }
}
