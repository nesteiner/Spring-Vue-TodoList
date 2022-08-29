import Axios from "axios"

class TaskListService {
    static instance = Axios.create({
        baseURL: "http://localhost:8082/tasklist"
    })

    public static async insertOne(tasklist: TaskList) {
        let response = await this.instance.post("/", tasklist);
        return response.data
    }

    public static async deleteOne(id: number) {
        let response = await this.instance.delete("/", {
            params: {
                id
            }
        })

        return response.data
    }

    public static async updateOne(tasklist: TaskList) {
        let response = await this.instance.put("/", tasklist);
        return response.data
    }

    public static async findAll(groupid: number) {
        let response = await this.instance.get("/", {
            params: {
                groupid: groupid
            }
        })

        return response.data
    }

    public static async findOne(id: number) {
        let response = await this.instance.get(`/${id}`);
        return response.data
    }
}

export default TaskListService;