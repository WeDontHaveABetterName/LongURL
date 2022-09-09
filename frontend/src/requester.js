import axios from "axios";

export async function create(url, algorithm, length) {
    const response = await axios.post("/create", 
        {
            url: url,
            algorithm: algorithm,
            length: parseInt(length),
        },
        {
            headers: {
                "Content-Type": "application/json",
            },
            validateStatus: false,
        }
    );
    if (response.status === 200) {
        return response.data.hash;
    } else {
        throw new Error(response.data.message);
    }
}