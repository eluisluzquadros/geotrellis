/*
 * Copyright (c) 2014 Azavea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geotrellis.raster.mapalgebra.local

import geotrellis.raster._
import geotrellis.util.MethodExtensions


/**
 * Operation to And values.
 *
 * @note        NoData values will cause the results of this operation
 *              to be NODATA.
 * @note        If used with Double typed rasters, the values
 *              will be rounded to Ints.
 */
object And extends LocalTileBinaryOp {
  def combine(z1: Int, z2: Int) =
    if (isNoData(z1) || isNoData(z2)) NODATA
    else z1 & z2

  def combine(z1: Double, z2: Double) =
    if (isNoData(z1) || isNoData(z2)) Double.NaN
    else i2d(d2i(z1) & d2i(z2))
}

trait AndMethods extends MethodExtensions[Tile] {
  /** And a constant Int value to each cell. */
  def localAnd(i: Int): Tile = And(self, i)
  /** And a constant Int value to each cell. */
  def &(i: Int): Tile = localAnd(i)
  /** And a constant Int value to each cell. */
  def &:(i: Int): Tile = localAnd(i)
  /** And the values of each cell in each raster.  */
  def localAnd(r: Tile): Tile = And(self, r)
  /** And the values of each cell in each raster. */
  def &(r: Tile): Tile = localAnd(r)
  /** And the values of each cell in each raster.  */
  def localAnd(rs: Traversable[Tile]): Tile = And(self +: rs.toSeq)
  /** And the values of each cell in each raster. */
  def &(rs: Traversable[Tile]): Tile = localAnd(rs)
}
