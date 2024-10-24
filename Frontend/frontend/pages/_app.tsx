// pages/_app.tsx
import 'leaflet/dist/leaflet.css'; // Ensure Leaflet CSS is imported here
import '../styles/globals.css';
import {AppProps} from "next/dist/pages/_app";

function MyApp({ Component, pageProps }: AppProps) {
    return <Component {...pageProps} />;
}

export default MyApp;
