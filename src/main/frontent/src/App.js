import React, {useCallback, useEffect, useState} from 'react';
import './App.css';
import axios from 'axios';
import {useDropzone} from 'react-dropzone'

const UserProfiles = () => {
    const [userProfiles, setUserProfiles] = useState([]);
    const fetchUserProfiles = () => {
        axios.get('http://localhost:8080/user-profile').then((res) => {
            console.log(res);
            setUserProfiles(res.data);
        });
    };
    useEffect(() => {
        fetchUserProfiles();
    }, []);
    return userProfiles.map((userProfile, index) => {
        return (
            <div key={index}>
                {userProfile.id ? <img src={`http://localhost:8080/user-profile/${userProfile.id}/image/download`} /> : null}
                <br/>
                <br/>
                <h1>{userProfile.username}</h1>
                <p>ID: {userProfile.id}</p>
                <Dropzone id={userProfile.id}/>
                <br/>
            </div>
        );
    });
};


function Dropzone({id}) {
    const onDrop = useCallback(acceptedFiles => {
        const file = acceptedFiles[0];

        console.log(file);

        const formData = new FormData();
        formData.append("file", file);

        axios.post(`http://localhost:8080/user-profile/${id}/image/upload`,
            formData,
            {
                headers: {
                    "Content-type": "multipart/form-data"
                }
            }).then(() => {
            console.log("file upload successfully")
        })
            .catch((err) => {
                console.log(err);
            });
    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Drag 'n' drop profile image or click to select profile image</p>
            }
        </div>
    )
}

function App() {
    return (
        <div className="App">
            <UserProfiles/>
        </div>
    );
}

export default App;