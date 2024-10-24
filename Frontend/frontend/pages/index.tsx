import Head from 'next/head';
import LoadImages, { ImageInfo } from "@/pages/load_images"; // Import ImageInfo here
// import '../styles/globals.css';
import dynamic from 'next/dynamic';
// @ts-ignore
import { useState } from 'react';

const DynamicImageMap = dynamic(() => import('../components/ImageMap'), { ssr: false });

export default function Home() {
    const [imageInfos, setImageInfos] = useState<ImageInfo[]>([]);

    const handleLoadImages = (infos: ImageInfo[]) => {
        setImageInfos(infos);
    };

    return (
        <div>
            <Head>
                <title>Image Metadata App</title>
                <meta name="description" content="An app to load image metadata from a directory" />
                <link rel="icon" href="/favicon.ico" />
            </Head>

            <main>
                <h1>Welcome to the Image Metadata Loader</h1>
                <LoadImages onLoadImages={handleLoadImages} />
                {imageInfos.length > 0 && <DynamicImageMap imageInfos={imageInfos} />}
            </main>

            <footer>
                <p>Image Metadata Loader &copy; 2024</p>
            </footer>
        </div>
    );
}
