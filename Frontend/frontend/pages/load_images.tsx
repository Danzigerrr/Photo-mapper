// @ts-ignore
import { useState } from 'react';
import axios from 'axios';
// @ts-ignore
import { ReactElement, JSXElementConstructor, ReactNode, ReactPortal, AwaitedReactNode, Key } from 'react';

export interface ImageInfo {
    imageWidth: number;
    imageHeight: number;
    model: string;
    dateTimeOriginal: string;
    timeZoneOriginal: string;
    gpsLatitudeRef: string;
    gpsLatitude: string;
    gpsLongitudeRef: string;
    gpsLongitude: string;
    gpsAltitudeRef: string;
    gpsAltitude: string;
    detectedFileTypeName: string;
    detectedFileTypeLongName: string;
    fileName: string;
    fileSize: number;
}

const LoadImages = ({onLoadImages}: { onLoadImages: (infos: ImageInfo[]) => void }) => {
    const [directoryPath, setDirectoryPath] = useState<string>('');
    const [imageInfos, setImageInfos] = useState<ImageInfo[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [currentPage, setCurrentPage] = useState<number>(1);
    const imagesPerPage = 3; // Number of images per page

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setDirectoryPath(event.target.value);
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/api/loadimages/directory', null, {
                params: {directoryPath},
            });
            setImageInfos(response.data);
            setError(null);
            onLoadImages(response.data); // Call the passed function
        } catch (err) {
            console.error(err);
            setError('Failed to load images metadata. Please check the directory path and try again.');
        }
    };

    // Calculate total pages
    const totalPages = Math.ceil(imageInfos.length / imagesPerPage);

    // Get current images
    const currentImages = imageInfos.slice((currentPage - 1) * imagesPerPage, currentPage * imagesPerPage);

    // @ts-ignore
    return (
        <div>
            <h1>Load Images Metadata</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Directory Path:
                    <input
                        type="text"
                        value={directoryPath}
                        onChange={handleInputChange}
                        placeholder="Enter directory path"
                    />
                </label>
                <button type="submit">Load Metadata</button>
            </form>
            {error && <p style={{color: 'red'}}>{error}</p>}
            <div>
                {imageInfos.length > 0 && (
                    <div>
                        <h2>Image Metadata</h2>
                        {/* Pagination Controls */}
                        <div>
                            <button
                                onClick={() => setCurrentPage((prev: number) => Math.max(prev - 1, 1))}
                                disabled={currentPage === 1}
                            >
                                Previous
                            </button>
                            <span> Page {currentPage} of {totalPages} </span>
                            <button
                                onClick={() => setCurrentPage((prev: number) => Math.min(prev + 1, totalPages))}
                                disabled={currentPage === totalPages}
                            >
                                Next
                            </button>
                        </div>
                        <ul>
                            {currentImages.map((info: {
                                fileName: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                imageWidth: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                imageHeight: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                model: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                dateTimeOriginal: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                gpsLatitude: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                gpsLongitude: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                                fileSize: string | number | bigint | boolean | ReactElement<any, string | JSXElementConstructor<any>> | Iterable<ReactNode> | ReactPortal | Promise<AwaitedReactNode> | null | undefined;
                            }, index: Key | null | undefined) => (
                                <li key={index}>
                                    <p><strong>File Name:</strong> {info.fileName}</p>
                                    <p><strong>Image Width:</strong> {info.imageWidth} px</p>
                                    <p><strong>Image Height:</strong> {info.imageHeight} px</p>
                                    <p><strong>Model:</strong> {info.model}</p>
                                    <p><strong>Date/Time Original:</strong> {info.dateTimeOriginal}</p>
                                    <p><strong>GPS Latitude:</strong> {info.gpsLatitude}</p>
                                    <p><strong>GPS Longitude:</strong> {info.gpsLongitude}</p>
                                    <p><strong>File Size:</strong> {info.fileSize} bytes</p>
                                    <hr />
                                </li>
                            ))}
                        </ul>

                    </div>
                )}
            </div>
        </div>
    );
};

export default LoadImages;
