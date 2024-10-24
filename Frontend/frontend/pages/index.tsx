import Head from 'next/head';
import LoadImages from "@/pages/load_images";
import '../styles/globals.css';

export default function Home() {
    return (
        <div>
            <Head>
                <title>Image Metadata App</title>
                <meta name="description" content="An app to load image metadata from a directory" />
                <link rel="icon" href="/favicon.ico" />
            </Head>

            <main>
                <h1>Welcome to the Image Metadata Loader</h1>
                <LoadImages />
            </main>

            <footer>
                <p>Image Metadata Loader &copy; 2024</p>
            </footer>
        </div>
    );
}
