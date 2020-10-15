import { Component, OnInit } from '@angular/core';

import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { BasicCountService } from './basic-count.service';

@Component({
  selector: 'jhi-basic-count',
  templateUrl: './basic-count.component.html',
  styleUrls: ['./basic-count.component.scss']
})
export class BasicCountComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
