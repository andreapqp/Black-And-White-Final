<template>
  <md-card class="md-card-profile">
    <div class="md-card-avatar">
      <img class="img" :src="cardUserImage" />
    </div>

    <md-card-content>
      <h6 class="category text-gray">CEO / Co-Founder</h6>
      <h4 class="card-title">Alec Thompson</h4>
      <p class="card-description">
        No I can't love you anymore,
I'm closing all my doors for you,
Cause I can't go back like before,
I'm stronger than you know,
It's done,
Now baby i'm so done
You got me wrong,
Thought I wasn't strong enough without you,
But i'm so done,
I'm moving on,
I'm gonna be stronger now without you,
But i'm so done
      </p>
      <md-button class="md-round md-success">Follow</md-button>
    </md-card-content>
  </md-card>
</template>
<script>
export default {
  name: "user-card",
  props: {
    cardUserImage: {
      type: String,
      default: require("@/assets/img/faces/better.png"),
    },
  },
  data() {
    return {};
  },
};
</script>
<style></style>
