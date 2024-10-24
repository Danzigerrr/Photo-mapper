// pages/_app.tsx
import '../styles/globals.css';
import 'leaflet/dist/leaflet.css'; // Correct way to import Leaflet CSS

function MyApp({ Component, pageProps }) {
    return <Component {...pageProps} />;
}

export default MyApp;
