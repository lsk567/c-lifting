// From AsyncCallbackDrop.lf
instant_t elapsed_time = lf_time_logical_elapsed();
printf("Asynchronous callback %d: Assigned logical time greater than start time by %lld nsec.\n", self->i++, elapsed_time);
if (elapsed_time <= self->expected_time) {
    printf("ERROR: Expected logical time to be larger than %lld.\n", self->expected_time);
    exit(1);
}
if (a->value != 0) {
    printf("ERROR: Received: %d, expected 0 because the second event should have been dropped.\n", a->value);
    exit(2);
}
if (self->toggle) {
    self->toggle = false;
    self->expected_time += 200000000LL;
} else {
    self->toggle = true;
}