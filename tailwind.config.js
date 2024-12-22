/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/main/resources/**/*.{html,js}"],
  theme: {

    extend: {
        screens: {
            'cm': '1100px'
        }
    },
  },
  plugins: [],
  darkMode: "selector",
};
