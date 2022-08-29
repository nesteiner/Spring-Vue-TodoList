import Axios from "axios"

class TaskService {
    static instance = Axios.create({
        baseURL: "http://localhost:8082/task"
    })

    public static async insertOne(task: Task) {
        let response = await this.instance.post("/", task);
        return response.data
    }

    public static async deleteOne(id: number) {
        let response = await this.instance.delete("/", {
            params: {
                id
            }
        });

        return response.data
    }

    public static async updateOne(task: Task) {
        let response = await this.instance.put("/", task);
        return response.data
    }

    public static async findAll(listid: number) {
        let response = await this.instance.get("/", {
            params: {
                listid
            }
        })

        return response.data
    }

    public static async findOne(id: number) {
        let response = await this.instance.get(`/${id}`)
        return response.data
    }
}

export default TaskService;