
class DataProcessor {

    extractMonthLabels(data) {
        return data.map(d => (d.month < 10 ? "0" + d.month : d.month) + "." + d.year)
    }

    extractYearLabels(data) {
        return data.map(d => d.year)
    }

    extractMovieDataset(data) {
        return data.map(d => d.movieCount)
    }

    extractMovieRates(data) {
        return data.map(d => parseFloat(d.movieAverageRate))
            .map(num => Math.round((num + Number.EPSILON) * 100) / 100)

    }

    extractFlatCases(data) {
        return data.map(d => d.cases)
    }

    extractFlatDeaths(data) {
        return data.map(d => d.deaths)
    }

    groupByGenres(data) {
        let allGenres = data.map(d => d.genre);
        allGenres = [...new Set(allGenres)]
        let output = []

        for (let i = 0; i < allGenres.length; i++) {
            let filtered = data.filter(d => d.genre === allGenres[i]);
            let obj = {}
            obj.label = allGenres[i]
            obj.data = filtered.map(f => f.movieCount)
            obj.backgroundColor = this.#selectColor(i, allGenres.length)
            obj.stack = 'Stack 0'
            output.push(obj)
        }
        return output
    }

    groupByType(data) {
        let allTypes = data.map(d => d.type);
        allTypes = [...new Set(allTypes)]
        let output = []

        for (let i = 0; i < allTypes.length; i++) {
            let filtered = data.filter(d => d.type === allTypes[i]);
            let obj = {}
            obj.label = allTypes[i]
            obj.data = filtered.map(f => f.movieCount)
            obj.backgroundColor = this.#selectColor(i, allTypes.length)
            obj.stack = 'Stack 0'
            output.push(obj)
        }
        return output
    }

    #selectColor(colorNum, colors){
        if (colors < 1) colors = 1; // defaults to one color - avoid divide by zero
        return "hsl(" + (colorNum * (360 / colors) % 360) + ",100%,50%)";
    }
}

export default new DataProcessor()