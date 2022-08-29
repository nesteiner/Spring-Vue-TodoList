import Axios from "axios";

class ListGroupService {
    static instance = Axios.create({
        baseURL: "http://localhost:8082/listgroup"
    })

    public static async insertOne(listgroup: ListGroup) {
        let response = await this.instance.post("/", listgroup);
        return response.data;
    }

    public static async deleteOne(id: number) {
        let response = await this.instance.delete("/", {
            params: {
                id
            }
        });

        return response.data
    }

    public static async updateOne(listgroup: ListGroup) {
        let response = await this.instance.put("/", listgroup);
        return response.data
    }

    public static async findAll() {
        let response = await this.instance.get("/")
        return response.data
    }
}

export default ListGroupService;