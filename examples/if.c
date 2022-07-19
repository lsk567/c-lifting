int i = 0;
i = i + 1;
if (1 < 2) {
	i += 1;
	lf_set(out, i);
} else {
	if (self->s != 10)
		lf_set(out, 0);
	else if (in->value == 3)
		lf_schedule(a, 0, 3);
	else {
		i = 2;
		i += 10;
	}
}
