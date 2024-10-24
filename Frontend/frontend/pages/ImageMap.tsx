// pages/ImageMap.tsx
import React from 'react';
import { MapContainer, Marker, Popup, TileLayer, useMap } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';
import { useEffect } from 'react';

interface ImageInfo {
    gpsLatitude: string;
    gpsLongitude: string;
    fileName: string;
}

interface ImageMapProps {
    imageInfos: ImageInfo[];
}

// Custom hook to set the map view based on markers
const SetMapView = ({ imageInfos }: { imageInfos: ImageInfo[] }) => {
    const map = useMap();

    useEffect(() => {
        if (imageInfos.length > 0) {
            const latitudes = imageInfos.map(info => parseFloat(info.gpsLatitude));
            const longitudes = imageInfos.map(info => parseFloat(info.gpsLongitude));
            const bounds = L.latLngBounds(
                latitudes.map((lat, index) => L.latLng(lat, longitudes[index]))
            );
            map.fitBounds(bounds); // Automatically adjusts the view to fit all markers
        }
    }, [imageInfos, map]);

    return null;
};

const ImageMap: React.FC<ImageMapProps> = ({ imageInfos }) => {
    return (
        <div style={{ height: '500px' }}>
            <MapContainer center={[51.505, -0.09]} zoom={2} style={{ height: '100%', width: '100%' }}>
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                />
                <SetMapView imageInfos={imageInfos} /> {/* Custom hook to adjust map view */}
                {imageInfos.map((info, index) => {
                    const position: [number, number] = [parseFloat(info.gpsLatitude), parseFloat(info.gpsLongitude)];
                    return (
                        <Marker key={index} position={position} icon={L.icon({
                            iconUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d1/Google_Maps_pin.svg/800px-Google_Maps_pin.svg.png', // Specify a custom marker icon
                            iconSize: [25, 41],
                            iconAnchor: [12, 41],
                            popupAnchor: [1, -34],
                            shadowSize: [41, 41]
                        })}>
                            <Popup>
                                <div style={{ textAlign: 'center' }}>
                                    <strong>{info.fileName}</strong>
                                    <br />
                                    <p>Latitude: {info.gpsLatitude}</p>
                                    <p>Longitude: {info.gpsLongitude}</p>
                                </div>
                            </Popup>
                        </Marker>
                    );
                })}
            </MapContainer>
        </div>
    );
};

export default ImageMap;
