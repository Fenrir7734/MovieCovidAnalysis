<template>
  <div class="container-fluid vertical-center ">
    <div class="row vw-100 mt-5 mb-5">
      <div class="rounded d-flex justify-content-center ">
        <div class="col-lg-10 col-sm-12 shadow-lg p-5 bg-light justify-content-center">
          <canvas id="myChart" width="400" height="600px"></canvas>
        </div>
      </div>
    </div>
  </div>
  <div class="container-fluid vertical-center ">
    <div class="row vw-100 mt-5 mb-5">
      <div class="rounded d-flex justify-content-center ">
        <div class="col-lg-10 col-sm-12 shadow-lg p-5 bg-light justify-content-center">
          <select @change="chartChanged" class="form-control text-center">
            <option value="1">Movie premiere count</option>
            <option value="2">Movie count</option>
            <option value="3">Movie count by genre</option>
            <option value="4">Movie genre popularity</option>
            <option value="5">Movie count by type</option>
            <option value="6">Movie type popularity</option>
            <option value="7">Average movie rate</option>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div v-if="isAdmin" class="container-fluid vertical-center ">
    <div class="row vw-100 mt-5 mb-5">
      <div class="rounded d-flex justify-content-center ">
        <div class="col-lg-10 col-sm-12 shadow-lg p-5 bg-light justify-content-center">
          <div class="row">
            <div class="col-lg-6 col-md-12">
              <button type="button" class="form-control btn-primary" @click="downloadJson">Export as JSON</button>
            </div>
            <div class="col-lg-6 col-md-12">
              <button type="button" class="form-control btn-primary" @click="downloadXml">Export as XML</button>
            </div>
          </div>
          <div class="row mt-4">
            <div class="col-lg-6 col-md-12">
              <form @submit.prevent="uploadJson">
                <label for="formFile" class="form-label">Import from JSON</label>
                <input class="form-control" type="file" id="formJsonFile">
                <input type="submit" class="mt-2 form-control btn-primary" value="Upload">
              </form>
            </div>
            <div class="col-lg-6 col-md-12">
              <form @submit.prevent="uploadXml">
                <label for="formFile" class="form-label">Import from XML</label>
                <input class="form-control" type="file" id="formXmlFile">
                <input type="submit" class="mt-2 form-control btn-primary" value="Upload">
              </form>
            </div>
          </div>
          <div v-if="msg" class="row mt-5">
            <div :class="[error ? 'col-12 text-center text-danger' : 'col-12 text-center text-success']">
              {{ msg }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import StatisticsService from "@/services/StatisticsService";
import ChartCreator from "@/chart/ChartCreator";
import DataProcessor from "@/chart/DataProcessor";
import ResourceService from "@/services/ResourceService";

export default {
  name: "DashBoard",
  data() {
    return {
      stats: [],
      msg: "",
      error: false
    }
  },
  methods: {
    chartChanged(e) {
      switch (e.target.value) {
        case "1":
          this.createPremiereCountMMChart()
          break
        case "2":
          this.createMovieCountMMChart()
          break
        case "3":
          this.createGenreCountMMChart()
          break
        case "4":
          this.createGenrePopularityMMChart()
          break
        case "5":
          this.createTypeCountMMChart()
          break
        case "6":
          this.createTypePopularityMMChart()
          break
        case "7":
          this.createMovieAverageRateChart()
          break
      }
    },
    async createPremiereCountMMChart() {
      const response = await StatisticsService.moviePremiere()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createMxM(
          ctx,
          {
            movieData: DataProcessor.extractMovieDataset(this.stats),
            cases: DataProcessor.extractFlatCases(this.stats),
            deaths: DataProcessor.extractFlatDeaths(this.stats),
            labels: DataProcessor.extractMonthLabels(this.stats)
          }
        )
    },

    async createMovieCountMMChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieCount()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createMxY(
          ctx,
          {
            movieData: DataProcessor.extractMovieDataset(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },

    async createGenreCountMMChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieGenreCount()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createStackMxY(
          ctx,
          {
            movieData: DataProcessor.groupByGenres(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },
    async createGenrePopularityMMChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieGenrePopularity()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createStackMxY(
          ctx,
          {
            movieData: DataProcessor.groupByGenres(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },
    async createTypeCountMMChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieTypeCount()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createStackMxY(
          ctx,
          {
            movieData: DataProcessor.groupByType(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },
    async createTypePopularityMMChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieTypePopularity()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createStackMxY(
          ctx,
          {
            movieData: DataProcessor.groupByType(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },
    async createMovieAverageRateChart() {
      let response = await StatisticsService.moviePremiere()
      let covidStats = response.data
      response = await StatisticsService.movieAverageRate()
      this.stats = response.data

      const ctx = document.getElementById("myChart").getContext("2d")
      ChartCreator.createMxY(
          ctx,
          {
            movieData: DataProcessor.extractMovieRates(this.stats),
            cases: DataProcessor.extractFlatCases(covidStats),
            deaths: DataProcessor.extractFlatDeaths(covidStats),
            labels: DataProcessor.extractMonthLabels(covidStats)
          }
      )
    },
    downloadJson() {
      ResourceService.download("json")
          .then(response => {
            this.handleDownloadResponse(response, "json")
      })
    },
    downloadXml() {
      ResourceService.download("xml")
          .then(response => {
            this.handleDownloadResponse(response, "xml")
          })
    },
    handleDownloadResponse(response, format) {
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a')
      link.href = url;
      link.setAttribute('download', `export.${format}`)
      document.body.appendChild(link)
      link.click()
    },
    uploadJson() {
      this.upload("formJsonFile", "json")
    },
    uploadXml() {
      this.upload("formXmlFile", "xml")
    },
    upload(sourceId, format) {
      const file = document.getElementById(sourceId).files[0]
      const formData = new FormData()
      formData.append("file", file)
      ResourceService.upload(format, formData)
          .then(response => {
            if (response.status === 204) {
              this.msg = "Data imported"
              this.error = false
            } else {
              this.msg = "Data malformed"
              this.error = true
            }
          }).catch(() => {
            this.msg = "Data malformed"
            this.error = true
      })
    }
  },
  mounted() {
    this.createPremiereCountMMChart()
  }
}
</script>

<style scoped>

</style>