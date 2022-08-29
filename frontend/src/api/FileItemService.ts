import Axios from "axios"

class FileItemService {
    static instance = Axios.create({
        baseURL: "http://localhost:8082/fileitem"
    })

    public static async insertOne(taskid: number, file: File) {
        let formdata = new FormData();
        formdata.append("taskid", `${taskid}`);
        formdata.append("file", file);
        let response = await this.instance.post("/", formdata);
        return response.data
    }

    public static async deleteOne(id: number) {
        let response = await this.instance.delete("/", {
            params: {
                id
            }
        });

        return response.data;
    }

    public static async findAll(taskid: number) {
        let response = await this.instance.get("/", {
            params: {
                taskid
            }
        })

        return response.data
    }

    public static async findOne(id: number) {
        let response = await this.instance.get(`/${id}`)
        return response.data
    }
}

export default FileItemService;