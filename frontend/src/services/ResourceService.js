import http from '../http-common'
import authHeader from "@/services/AuthHeader";

class ResourceService {
    download(format) {
        return http.get(`/res/export?format=${format}`, { headers: authHeader(), responseType: 'blob' })
    }

    upload(format, file) {
        let header = authHeader()
        header['Content-Type'] = 'multipart/form-data'
        return http.post(`/res/import?format=${format}`, file, { headers: header })
    }
}

export default new ResourceService();